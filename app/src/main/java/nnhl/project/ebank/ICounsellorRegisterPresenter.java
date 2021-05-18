package nnhl.project.ebank;

public interface ICounsellorRegisterPresenter {
    void register (String username, String name, String password, String email, String phone, String SID);
    void register_callback (int status, String msg);
}
