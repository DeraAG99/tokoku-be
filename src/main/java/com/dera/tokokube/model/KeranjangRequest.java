package com.dera.tokokube.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeranjangRequest implements Serializable {

    private String produkId;
    private Double kuantitas;
}
