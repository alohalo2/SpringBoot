package com.bit.springboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardFileDto {
    private int id;
    private int board_id;
    private String filename;
    private String fileoriginname;
    private String filepath;
    private String filetype;
    private String filestatus;
    private String newfilename;
}
