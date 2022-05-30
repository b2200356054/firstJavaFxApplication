public class Users {
    private final String username; private final String password;
    private boolean admin; private boolean clubMember;
    public Users(String username, String password, String admin, String clubMember){
        this.username = username;
        this.password = password;
        this.admin = admin.equals("true");
        this.clubMember = clubMember.equals("true");


    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public boolean isClubMember() {
        return clubMember;
    }
    public void setClubMember(boolean club) {
        this.clubMember = club;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
