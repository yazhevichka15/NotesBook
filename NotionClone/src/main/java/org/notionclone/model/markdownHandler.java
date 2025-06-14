package org.notionclone.model;

import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

public class markdownHandler {

    public static String renderMd(String mdContent){
        Parser parser = Parser.builder().extensions(List.of(TablesExtension.create())).build();
        Node document = parser.parse(mdContent);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();

        return renderer.render(document);
    }
}
