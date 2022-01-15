package cj.dataframe;

import cj.dataframe.api.DataFrame;
import com.qiniu.defy.statd.config.Storage;
import com.qiniu.defy.statd.search.Search;
import com.qiniu.defy.statd.storage.StorageReader;
import com.qiniu.defy.statd.storage.StorageReaderFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataFrameReader implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final StorageReaderFactory storageReaderFactory;

    @Autowired
    public DataFrameReader(StorageReaderFactory storageReaderFactory) {
        this.storageReaderFactory = storageReaderFactory;
    }

    private StorageReader<Storage> getReader(Storage storage) {
        return applicationContext.getBean(storage.getReaderClass());
    }

    public DataFrame load(Search search, Storage storage) {
        return getReader(storage).read(search, storage);
    }

    public DataFrame load(Search search, String storageName) {
        Optional<Storage> storage = storageReaderFactory.getStorage(storageName);
        if (!storage.isPresent()) {
            throw new IllegalArgumentException(String.format("storage not found for '%s'", storageName));
        }
        return load(search, storage.get());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
