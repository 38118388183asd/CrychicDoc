package com.github.alexthe666.citadel.repack.jaad.mp4.boxes.impl;

import com.github.alexthe666.citadel.repack.jaad.mp4.MP4InputStream;
import com.github.alexthe666.citadel.repack.jaad.mp4.boxes.FullBox;
import java.io.IOException;

public class DataEntryUrnBox extends FullBox {

    private boolean inFile;

    private String referenceName;

    private String location;

    public DataEntryUrnBox() {
        super("Data Entry Urn Box");
    }

    @Override
    public void decode(MP4InputStream in) throws IOException {
        super.decode(in);
        this.inFile = (this.flags & 1) == 1;
        if (!this.inFile) {
            this.referenceName = in.readUTFString((int) this.getLeft(in), "UTF-8");
            if (this.getLeft(in) > 0L) {
                this.location = in.readUTFString((int) this.getLeft(in), "UTF-8");
            }
        }
    }

    public boolean isInFile() {
        return this.inFile;
    }

    public String getReferenceName() {
        return this.referenceName;
    }

    public String getLocation() {
        return this.location;
    }
}