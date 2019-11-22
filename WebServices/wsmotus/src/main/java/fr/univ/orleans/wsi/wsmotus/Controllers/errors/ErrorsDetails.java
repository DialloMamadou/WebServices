package fr.univ.orleans.wsi.wsmotus.Controllers.errors;

public class ErrorsDetails {
    private String message;
    private String details;

    public ErrorsDetails(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
