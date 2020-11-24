package entity;

/**
 * @auther Skay
 * @date 2020/10/20 15:33
 * @description
 */
public class Article {
    private String title;
    private String tag;
    private String description;
    private String content;
    private String time;
    private String editor;
    private String is_private;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getEditor() {
        return editor;
    }

    public String getIs_private() {
        return is_private;
    }

    public String getTag() {
        return tag;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setIs_private(String is_private) {
        this.is_private = is_private;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
