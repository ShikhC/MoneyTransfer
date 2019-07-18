package com.moneytransfer.demo.data.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class ErrorResponse {
    private String txnId;
    private String cause;
    private String message;
    private String description;
    private int httpCode;
    private String errorCode;

    public String getTxnId() {
        return this.txnId;
    }

    public String getCause() {
        return this.cause;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDescription() {
        return this.description;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ErrorResponse)) {
            return false;
        } else {
            ErrorResponse other = (ErrorResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label75:
                {
                    Object this$txnId = this.getTxnId();
                    Object other$txnId = other.getTxnId();
                    if (this$txnId == null) {
                        if (other$txnId == null) {
                            break label75;
                        }
                    } else if (this$txnId.equals(other$txnId)) {
                        break label75;
                    }

                    return false;
                }

                Object this$cause = this.getCause();
                Object other$cause = other.getCause();
                if (this$cause == null) {
                    if (other$cause != null) {
                        return false;
                    }
                } else if (!this$cause.equals(other$cause)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                label54:
                {
                    Object this$description = this.getDescription();
                    Object other$description = other.getDescription();
                    if (this$description == null) {
                        if (other$description == null) {
                            break label54;
                        }
                    } else if (this$description.equals(other$description)) {
                        break label54;
                    }

                    return false;
                }

                if (this.getHttpCode() != other.getHttpCode()) {
                    return false;
                } else {
                    Object this$errorCode = this.getErrorCode();
                    Object other$errorCode = other.getErrorCode();
                    if (this$errorCode == null) {
                        if (other$errorCode != null) {
                            return false;
                        }
                    } else if (!this$errorCode.equals(other$errorCode)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ErrorResponse;
    }

    public int hashCode() {
        int result = 1;
        Object $txnId = this.getTxnId();
        result = result * 59 + ($txnId == null ? 43 : $txnId.hashCode());
        Object $cause = this.getCause();
        result = result * 59 + ($cause == null ? 43 : $cause.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        result = result * 59 + this.getHttpCode();
        Object $errorCode = this.getErrorCode();
        result = result * 59 + ($errorCode == null ? 43 : $errorCode.hashCode());
        return result;
    }

    public String toString() {
        return "ErrorResponse(txnId=" + this.getTxnId() + ", cause=" + this.getCause() + ", message=" + this.getMessage() + ", description=" + this.getDescription() + ", httpCode=" + this.getHttpCode() + ", errorCode=" + this.getErrorCode() + ")";
    }

    public ErrorResponse(String txnId, String cause, String message, String description, int httpCode, String errorCode) {
        this.txnId = txnId;
        this.cause = cause;
        this.message = message;
        this.description = description;
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ErrorResponse() {
    }
}