package com.nfjs.icndb.app;

public class IcndbJoke {
    private String type;
    private JsonJoke value;

    public String getJoke() {
        return value.getJoke();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonJoke getValue() {
        return value;
    }

    public void setValue(JsonJoke value) {
        this.value = value;
    }

    private static class JsonJoke {
        private int id;
        private String joke;
        private String[] categories;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJoke() {
            return joke;
        }

        public void setJoke(String joke) {
            this.joke = joke;
        }

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }
}
