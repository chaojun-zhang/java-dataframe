package cj.dataframe.tree;

import io.vavr.PartialFunction;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;


public interface TreeNode<Base extends TreeNode<Base>> extends Product {

    Seq<Base> children();

    @Override
    default Seq<Object> productElements() {
        return children().map(it -> it);
    }

    default boolean fastEquals(Base that) {
        return this.equals(that);
    }

    default Base transformDown(Rule<Base> rule) {
        Base newSelf = rule.apply((Base) this);
        return newSelf.transformChildren(rule, TreeNode::transformDown);
    }

    default Base transformUp(Rule<Base> rule) {
        Base newSelf = this.transformChildren(rule, TreeNode::transformUp);
        return rule.apply((Base) newSelf);
    }

    default Base transformChildren(Rule<Base> rule, BiFunction<Base, Rule<Base>, Base> next) {
        Seq<Base> newChildren = children().map(it -> next.apply(it, rule));
        boolean anyChildChanged = newChildren.zip(children()).map(it -> it._1.fastEquals(it._2)).contains(false);
        if (anyChildChanged) {
            return withChildren(newChildren);
        } else {
            return (Base) this;
        }
    }

    default Base withChildren(Seq<Base> newChildren) {
        assert (newChildren.size() == children().size());
        List<Base> remainingNewChildren = newChildren.toList();
        AtomicBoolean changed = new AtomicBoolean(false);
        Function<Base, Base> mapChild = child -> {
            Base newChild = remainingNewChildren.head();
            remainingNewChildren.removeAt(0);
            changed.set(changed.get() || !(newChild.fastEquals(child)));
            return newChild;
        };

        Array<Object> newArgs = productElements().map(it -> {
            if (it instanceof TreeNode<?>) {
                Base treeNode = (Base) it;
                if (children().contains(treeNode)) {
                    return mapChild.apply(treeNode);
                }
            } else if (it instanceof Traversable<?>) {
                return ((Traversable<?>) it).map(child -> {
                    if (child instanceof TreeNode<?>) {
                        Base treeNode = (Base) it;
                        if (children().contains(treeNode)) {

                            return mapChild.apply(treeNode);
                        }
                    }
                    return child;
                });
            }
            return it;
        }).toArray();

        if (changed.get()) {
            return makeCopy(newArgs);
        } else {
            return (Base) this;
        }
    }

    private Base makeCopy(Array<Object> args) {
        Constructor<?> defaultConstructor = List.ofAll(Arrays.stream(this.getClass().getConstructors()))
                .filter(it -> it.getParameterTypes().length > 0)
                .maxBy(it -> it.getParameterTypes().length).get();
        try {
            return (Base) defaultConstructor.newInstance(args.toJavaArray());
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to instantiate %s", this.getClass().getName()), e);
        }
    }

    default <T> Seq<T> collectDown(PartialFunction<Base, T> function) {
        java.util.List<T> buffer = new ArrayList<>();
        this.transformDown(new Rule<>() {
            @Override
            public Base apply(Base base) {
                T it = function.apply(base);
                buffer.add(it);
                return (Base) it;
            }

            @Override
            public boolean isDefinedAt(Base value) {
                return function.isDefinedAt(value);
            }
        });
        return List.ofAll(buffer);
    }

    default <T> Seq<T> collectUp(PartialFunction<Base, T> function) {
        java.util.List<T> buffer = new ArrayList<>();
        this.transformUp(new Rule<>() {
            @Override
            public Base apply(Base base) {
                T it = function.apply(base);
                buffer.add(it);
                return (Base) it;
            }

            @Override
            public boolean isDefinedAt(Base value) {
                return function.isDefinedAt(value);
            }
        });
        return List.ofAll(buffer);
    }


}

