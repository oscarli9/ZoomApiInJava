package zoomapi.models;

public class Result {
    public static String PARSE_ERROR = "Parse error: ";
    public static String PARAMETER_ERROR = "Missing parameter error: ";
    public static String CHANNEL_ERROR = "No existing channel is named ";
    public static String TIME_RANGE_ERROR = "Time range error: ";

    private Object result = null;
    private String errorMessage = null;

    public boolean isSuccessful() {
        return result != null;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setErrorMessage(String errorType, String message) {
        this.errorMessage = errorType + message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
