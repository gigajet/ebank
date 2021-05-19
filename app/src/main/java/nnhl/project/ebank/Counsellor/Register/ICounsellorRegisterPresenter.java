package nnhl.project.ebank.Counsellor.Register;

import nnhl.project.ebank.Counsellor.CounsellorModel;

public interface ICounsellorRegisterPresenter {
    void register (CounsellorModel counsellor, String SID);
    void register_callback (int status, String msg);
}