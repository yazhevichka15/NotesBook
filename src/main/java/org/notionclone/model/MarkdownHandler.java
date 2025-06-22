package org.notionclone.model;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

import java.util.List;

public class MarkdownHandler {
    private static boolean darkTheme = false;

    public static void setDarkTheme(boolean dark) {
        darkTheme = dark;
    }

    public static boolean isDarkTheme() {
        return darkTheme;
    }

    public static String RenderMd(String mdContent) {
        Parser parser = Parser.builder().extensions(List.of(TablesExtension.create())).build();
        Node document = parser.parse(mdContent);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(List.of(TablesExtension.create())).build();
        String htmlBody = renderer.render(document);

        htmlBody = htmlBody
                .replaceAll("<li>\\s*\\[ \\]\\s*", "<li><input type='checkbox' disabled> ")
                .replaceAll("<li>\\s*\\[-\\]\\s*", "<li><input type='checkbox' checked disabled> ");

        return stylizeHtml(htmlBody);
    }

    private static String stylizeHtml(String contentToRender) {
        String themeColors = darkTheme ? """
                background-color: #2F2F2F;
                color: #ffffff;
            """ : """
                background-color: #ffffff;
                color: #000000;
            """;

        String htmlTemplate = """
            <html>
            <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    font-size: 16pt;
                    padding: 20px;
                    overflow: auto;
                    scrollbar-width: none;
                    -ms-overflow-style: none;
                    %s
                }
    
                body::-webkit-scrollbar {
                    display: none;
                }
    
                ul {
                    list-style-type: disc;
                    padding-left: 20px;
                }
    
                ol {
                    list-style-type: decimal;
                    padding-left: 20px;
                }
    
                li {
                    margin-bottom: 10px;
                }
    
                li:has(input[type="checkbox"]) {
                    list-style-type: none;
                }
    
                input[type="checkbox"] {
                    appearance: none;
                    -webkit-appearance: none;
                    width: 18px;
                    height: 18px;
                    border: 2px solid #aaa;
                    border-radius: 3px;
                    position: relative;
                    cursor: default;
                    background-color: transparent;
                }
    
                input[type="checkbox"]:checked::after {
                    content: '✓';
                    position: absolute;
                    top: -2px;
                    font-size: 16px;
                    %s
                }
    
                input[type="checkbox"]:checked + span {
                    text-decoration: line-through;
                    color: #888;
                }
    
                li input[type="checkbox"] + span {
                    flex: 1;
                }
            </style>
            </head>
            <body>%s</body>
            </html>
            """;

        String checkboxColor = darkTheme ? "color: gray;" : "color: lightgray;";

        return htmlTemplate.formatted(themeColors, checkboxColor, contentToRender);
    }
}
