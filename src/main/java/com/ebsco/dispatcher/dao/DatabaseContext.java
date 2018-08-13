package com.ebsco.dispatcher.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author jShanmugam on 07/31/18.
 */
public abstract class DatabaseContext {

    private final static Logger LOG = LoggerFactory.getLogger(DatabaseContext.class);

    private final DynamoDB context;

    DatabaseContext(){
        Regions regionEnum = Regions.US_EAST_1;
        context = new DynamoDB(regionEnum);
    }

    DatabaseContext(DynamoDB context) {
        this.context = context;
    }

    /**
     * @param parseFunction
     */
    protected <T> Function<T, Item> create(Function<T, Item> parseFunction) {
        LOG.info("Creating entity in database");
        return t -> this.context.getTable(getTableName()).putItem(parseFunction.apply(t)).getItem();
    }

    /**
     * @param parseFunction
     */
    protected <T> Function<KeyAttribute, Optional<T>> get(Function<Item, Optional<T>> parseFunction) {
        LOG.info("Getting entity from database");
        return k -> parseFunction.apply(this.context.getTable(getTableName()).getItem(k));
    }

    /**
     * @param parseFunction
     */
    protected <T> Function<KeyAttribute, Optional<T>> delete(Function<Item, Optional<T>> parseFunction) {
        LOG.info("Deleting enttiy in database");
        return k -> parseFunction.apply(this.context.getTable(getTableName()).deleteItem(k).getItem());
    }

    /**
     * @return tableName
     */
    protected abstract String getTableName();
}
