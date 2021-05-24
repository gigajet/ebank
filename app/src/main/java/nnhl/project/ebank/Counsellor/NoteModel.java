package nnhl.project.ebank.Counsellor;

public class NoteModel {
    private String clientMail;
    private String clientName;
    private String noteContent;
    private String token;

    public NoteModel() {

    }

    public NoteModel(String clientMail, String clientName, String noteContent, String token) {
        this.clientMail = clientMail;
        this.clientName = clientName;
        this.noteContent = noteContent;
        this.token = token;
    }



    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
