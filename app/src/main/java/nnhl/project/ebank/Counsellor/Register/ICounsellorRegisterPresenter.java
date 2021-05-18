package nnhl.project.ebank.Counsellor.Register;

public interface ICounsellorRegisterPresenter {
    void register (String username, String name, String password, String email, String phone, String SID);
    void register_callback (int status, String msg);
}