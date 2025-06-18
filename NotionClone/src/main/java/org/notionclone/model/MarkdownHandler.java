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

    public static String RenderMd(String mdContent){
        Parser parser = Parser.builder().extensions(List.of(TablesExtension.create())).build();
        Node document = parser.parse(mdContent);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();

        return renderer.render(document);
    }
}


