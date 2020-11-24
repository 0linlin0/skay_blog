package entity;

/**
 * @auther Skay
 * @date 2020/10/20 15:33
 * @description
 */
public class Comment {
    private String which_art;
    private boolean is_see;
    private String overview_content;
    private String which_user;
    private String content;
    private String Id;

    public String getWhich_user() {
        return which_user;
    }

    public void setWhich_user(String which_user) {
        this.which_user = which_user;
    }


    public String getContent() {
        return content;
    }

    public void setOverview_content(String overview_content) {
        this.overview_content = overview_content;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public boolean isIs_see() {
        return is_see;
    }

    public void setIs_see(boolean is_see) {
        this.is_see = is_see;
    }



    public String getWhich_art() {
        return which_art;
    }

    public void setWhich_art(String which_art) {
        this.which_art = which_art;
    }

    public void setcontent(String content) {this.content = content;
    }

    public Object gercontent() {
        return content;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = (String) id;
    }
}
