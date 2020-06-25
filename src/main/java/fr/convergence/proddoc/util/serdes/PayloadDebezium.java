package fr.convergence.proddoc.util.serdes;

import io.vertx.core.json.JsonObject;

public class PayloadDebezium {

    private Object after;
    private SourceDebezium source;

    public SourceDebezium getSource() {
        return source;
    }

    public void setSource(SourceDebezium source) {
        this.source = source;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }
}
