package blog.services;

public interface NotificationService {
    void addInfoMessage(String msg);
    void addWarningMessage(String msg);
    void addErrorMessage(String msg);
}
