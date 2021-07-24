package com.esmc.mcnp.commons.model;

import lombok.Data;

@Data
public class SysFile {
    private Integer fileId;
    private String name;
    private Integer parentId;
    private String parentPath;
    private boolean directory;
}
