package nnhl.project.ebank;

public interface ICounsellorRegisterPresenter {
    void register (String name, String email, String phone, String SID);
    void register_callback (int status, String msg);
}
