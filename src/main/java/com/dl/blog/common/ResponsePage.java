package com.dl.blog.common;

public class ResponsePage {

    public interface Manage{
        public static final String MANAGE_PREINDEX="backstage/manage_preindex";
        public static final String MANAGE_INDEX="backstage/manage_index";
        public static final String MANAGE_LOGIN="backstage/manage_login";
        public static final String MANAGE_TYPE="backstage/manage_type";
        public static final String MANAGE_TYPE_INPUT="backstage/manage_type_input";
        public static final String MANAGE_TAG="backstage/manage_tag";
        public static final String MANAGE_TAG_INPUT="backstage/manage_tag_input";
        public static final String MANAGE_PUBLISH="backstage/manage_publish";

    }
    public interface Web{
        public static final String ABOUT="about";
        public static final String ARCHIVE="archive";
        public static final String BLOGDETAILS="blogdetails";
        public static final String CLASSIFICATION="classification";
        public static final String INDEX="index";
        public static final String LABEL="label";
    }
    public interface Error{
        public static final String ERROR="error";
    }
}
