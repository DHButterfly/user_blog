package com.dl.blog.util;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.tables.TablesExtension;

import java.util.*;
//
//public class MarkdownToHTMLUtil {
//    /**
//     * markdown格式转换为html格式
//     * @param markdown
//     * @return
//     */
//    public static String markdownToHtml(String markdown){
//        Parser parser = Parser.builder().build();
//        Node document = parser.parse(markdown);
//        HtmlRenderer renderer = HtmlRenderer.builder().build();
//        return renderer.render(document);
//    }
//
//    /**
//     * 增加扩展[标题锚点、表格生成]
//     * markdown转换为html格式
//     * @param markdown
//     * @return
//     */
//    public static String markdownToHtmlExtensions(String markdown){
//        //h标题生成id
//        Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
//        //转换table的HTML
//        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
//        Parser parser = Parser.builder()
//                .extensions(tableExtensions)
//                .build();
//        Node document = parser.parse(markdown);
//        HtmlRenderer renderer = HtmlRenderer.builder()
//                .extensions(headingAnchorExtensions)
//                .extensions(tableExtensions)
//                .attributeProviderFactory(new AttributeProviderFactory() {
//                    @Override
//                    public AttributeProvider create(AttributeProviderContext context) {
//                        return new CusTomAttributeProvider();
//                    }
//                })
//                .build();
//        return renderer.render(document);
//    }
//
//    /**
//     * 处理标签属性
//     */
//    static class CusTomAttributeProvider implements AttributeProvider{
//
//        @Override
//        public void setAttributes(Node node, String s, Map<String, String> map) {
//            //改变标签的target属性未_blank,也就是点击链接以新窗口的方式打开
//            //用来判断引用类型的变量所指向的对象是否是一个类(或者接口,抽象类,父类)的实例
//            if(node instanceof Link){
//                map.put("target","_blank");
//            }
//            if(node instanceof TableBlock){
//                map.put("class","ui celled table");
//            }
//        }
//    }
//
//}
public class MarkdownToHTMLUtil {

    /**
     * markdown格式转换成HTML格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }

    public static void main(String[] args) {
        String string="h1,h2,h3,h4,h5,h6 {" +
                "line-height:1;font-family:Arial,sans-serif;margin:1.4em 0 0.8em;" +
                "} "+
                "h1{font-size:1.8em;}" +
                "h2{font-size:1.6em;}" +
                "h3{font-size:1.4em;}" +
                "h4{font-size:1.2em;}" +
                "h5,h6{font-size:1em;}" +
                "/* 现代排版：保证块/段落之间的空白隔行 */" +
                ".typo p, .typo pre, .typo ul, .typo ol, .typo dl, .typo form, .typo hr {下面的代码利用的是jquery的append加载google广告不错，百度的好像不能用。后加载百度的可以是用百度管家自带的函数。\n" +
                "margin:1em 0 0.6em;" +
                "}";

        String str2="|属性|描述|" +
                    "|:----:|:----:|" +
                    "|property|需要映射到JavaBean 的属性名称。|";
        System.out.println(markdownToHtmlExtensions(str2));
    }
}
