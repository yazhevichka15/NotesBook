package org.notionclone.model;

//import org.commonmark.ext.gfm.tables.TablesExtension;
//import org.commonmark.node.Node;
//import org.commonmark.parser.Parser;
//import org.commonmark.renderer.html.HtmlRenderer;



import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

import java.util.List;

public class MarkdownHandler {

//    public static String RenderMd(String mdContent){
//        Parser parser = Parser.builder().extensions(List.of(TablesExtension.create())).build();
//        Node document = parser.parse(mdContent);
//
//        HtmlRenderer renderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();
//
//        return renderer.render(document);
//    }

    private static boolean darkTheme = false;

    public static void setDarkTheme(boolean dark) {
        darkTheme = dark;
    }

    public static String RenderMd(String mdContent){
        Parser parser = Parser.builder().extensions(List.of(TablesExtension.create())).build();
        Node document = parser.parse(mdContent);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();
        String htmlBody = renderer.render(document);

        String css = darkTheme ?
                "body { background-color: #2F2F2F; color: #ffffff; font-family: Arial; }" :
                "body { background-color: #fff; color: #000; font-family: Arial; }";

        return "<html><head><style>" + css + "</style></head><body>" + htmlBody + "</body></html>";
    }
}


