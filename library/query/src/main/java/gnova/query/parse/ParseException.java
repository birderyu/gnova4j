package gnova.query.parse;

public class ParseException
        extends Exception {

    private final String whereClause;
    private final int errorCursor;

    public ParseException(String whereClause, int errorCursor) {
        super();
        this.whereClause = whereClause;
        this.errorCursor = errorCursor;
    }

    public ParseException(String errorMessage, String whereClause, int errorCursor) {
        super(errorMessage);
        this.whereClause = whereClause;
        this.errorCursor = errorCursor;
    }

    public ParseException(String whereClause, int errorCursor, Throwable throwable) {
        super(throwable);
        this.whereClause = whereClause;
        this.errorCursor = errorCursor;
    }

    public ParseException(String errorMessage, String whereClause, int errorCursor, Throwable throwable) {
        super(errorMessage, throwable);
        this.whereClause = whereClause;
        this.errorCursor = errorCursor;
    }

}