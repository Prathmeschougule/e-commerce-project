package com.ecom.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.StyledEditorKit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    public String message;
    private Boolean status;
}
