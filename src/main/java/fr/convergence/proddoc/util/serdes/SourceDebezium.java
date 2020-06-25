package fr.convergence.proddoc.util.serdes;

public class SourceDebezium {

    private String version;
    private String connector;
    private String name;
    private String ts_ms;
    private String snapshot;
    private String db;
    private String schema;
    private String table;
    private String txId;
    private String lsn;
    private String xmin;

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getLsn() {
        return lsn;
    }

    public void setLsn(String lsn) {
        this.lsn = lsn;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTs_ms() {
        return ts_ms;
    }

    public void setTs_ms(String ts_ms) {
        this.ts_ms = ts_ms;
    }
}
