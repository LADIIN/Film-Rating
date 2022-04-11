package com.epam.filmrating.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * Custom JSP tag for pagination.
 */
public class PaginationTag extends TagSupport {
    /**
     * Elements on page.
     */
    private int elementsOnPage;
    /**
     * Current page.
     */
    private int currentPage;
    /**
     * Total elements.
     */
    private int elements;
    /**
     * Page URI.
     */
    private String uri;

    /**
     * Forms page navigation bar.
     *
     * @return int
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
        int pages = (int) Math.ceil(elements * 1.0 / elementsOnPage);
        JspWriter out = pageContext.getOut();
        if (pages > 1) {
            try {
                out.write("<div class=\"pagination-wrapper\"><div class=\"pagination\">");
                if (currentPage > 1) {
                    out.write(getLinkElement(currentPage - 1, "&laquo;"));
                }
                for (int i = 1; i <= pages; i++) {
                    String element;
                    if (i == currentPage) {
                        element = getActiveElement(String.valueOf(i));
                    } else {
                        element = getLinkElement(i, String.valueOf(i));
                    }
                    out.write(element);
                }
                if (currentPage < pages) {
                    out.write(getLinkElement(currentPage + 1, "&raquo;"));
                }
                out.write("</div></div>");
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }

    /**
     * Returns link element of page navigation bar.
     * @param page
     * @param content
     * @return String
     */
    private String getLinkElement(int page, String content) {
        return String.format("<a href=\"%s&page=%d\">%s</a>", uri, page, content);
    }

    /**
     * Returns active element of page navigation bar.
     * @param content
     * @return String
     */
    private String getActiveElement(String content) {
        return String.format("<a class=\"active\">%s</a>", content);
    }

    public void setElementsOnPage(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
