package com.example.englishrussiandict.entity;

public class Dictionary {
    String _id;
    String word;
    String definition;
    String status;
    String user_created;

    public Dictionary() {
    }

    public Dictionary(String _id, String word, String definition, String status, String user_created) {
        this._id = _id;
        this.word = word;
        this.definition = definition;
        this.status = status;
        this.user_created = user_created;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_created() {
        return user_created;
    }

    public void setUser_created(String user_created) {
        this.user_created = user_created;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "_id='" + _id + '\'' +
                ", word='" + word + '\'' +
                ", definition='" + definition + '\'' +
                ", status='" + status + '\'' +
                ", user_created='" + user_created + '\'' +
                '}';
    }
}

