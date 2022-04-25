package gov.iti.jets.api.dtos;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
    private String error;
    private int code;
    private String desc;

    public ErrorMessage(String error, int code, String desc) {
        this.error = error;
        this.code = code;
        this.desc = desc;
    }
}
