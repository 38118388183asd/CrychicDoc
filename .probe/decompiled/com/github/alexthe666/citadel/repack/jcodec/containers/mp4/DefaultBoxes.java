package com.github.alexthe666.citadel.repack.jcodec.containers.mp4;

import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.Box;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.ChunkOffsets64Box;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.ChunkOffsetsBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.ClearApertureBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.ClipRegionBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.CompositionOffsetsBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.DataInfoBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.DataRefBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.EditListBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.EncodedPixelBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.FileTypeBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.GenericMediaInfoBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.HandlerBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.IListBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.KeysBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.LoadSettingsBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MediaBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MediaHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MediaInfoBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MetaBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieExtendsBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieExtendsHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieFragmentBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieFragmentHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.MovieHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.NameBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.NodeBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.PartialSyncSamplesBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.ProductionApertureBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SampleDescriptionBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SampleSizesBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SampleToChunkBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SegmentIndexBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SegmentTypeBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SoundMediaHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.SyncSamplesBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TimeToSampleBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TimecodeMediaInfoBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrackExtendsBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrackFragmentBaseMediaDecodeTimeBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrackFragmentBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrackFragmentHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrackHeaderBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrakBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.TrunBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.UdtaBox;
import com.github.alexthe666.citadel.repack.jcodec.containers.mp4.boxes.VideoMediaHeaderBox;

public class DefaultBoxes extends Boxes {

    public DefaultBoxes() {
        this.mappings.put(MovieExtendsBox.fourcc(), MovieExtendsBox.class);
        this.mappings.put(MovieExtendsHeaderBox.fourcc(), MovieExtendsHeaderBox.class);
        this.mappings.put(SegmentIndexBox.fourcc(), SegmentIndexBox.class);
        this.mappings.put(SegmentTypeBox.fourcc(), SegmentTypeBox.class);
        this.mappings.put(TrackExtendsBox.fourcc(), TrackExtendsBox.class);
        this.mappings.put(VideoMediaHeaderBox.fourcc(), VideoMediaHeaderBox.class);
        this.mappings.put(FileTypeBox.fourcc(), FileTypeBox.class);
        this.mappings.put(MovieBox.fourcc(), MovieBox.class);
        this.mappings.put(MovieHeaderBox.fourcc(), MovieHeaderBox.class);
        this.mappings.put(TrakBox.fourcc(), TrakBox.class);
        this.mappings.put(TrackHeaderBox.fourcc(), TrackHeaderBox.class);
        this.mappings.put("edts", NodeBox.class);
        this.mappings.put(EditListBox.fourcc(), EditListBox.class);
        this.mappings.put(MediaBox.fourcc(), MediaBox.class);
        this.mappings.put(MediaHeaderBox.fourcc(), MediaHeaderBox.class);
        this.mappings.put(MediaInfoBox.fourcc(), MediaInfoBox.class);
        this.mappings.put(HandlerBox.fourcc(), HandlerBox.class);
        this.mappings.put(DataInfoBox.fourcc(), DataInfoBox.class);
        this.mappings.put("stbl", NodeBox.class);
        this.mappings.put(SampleDescriptionBox.fourcc(), SampleDescriptionBox.class);
        this.mappings.put(TimeToSampleBox.fourcc(), TimeToSampleBox.class);
        this.mappings.put("stss", SyncSamplesBox.class);
        this.mappings.put("stps", PartialSyncSamplesBox.class);
        this.mappings.put(SampleToChunkBox.fourcc(), SampleToChunkBox.class);
        this.mappings.put(SampleSizesBox.fourcc(), SampleSizesBox.class);
        this.mappings.put(ChunkOffsetsBox.fourcc(), ChunkOffsetsBox.class);
        this.mappings.put("keys", KeysBox.class);
        this.mappings.put(IListBox.fourcc(), IListBox.class);
        this.mappings.put("mvex", NodeBox.class);
        this.mappings.put("moof", NodeBox.class);
        this.mappings.put("traf", NodeBox.class);
        this.mappings.put("mfra", NodeBox.class);
        this.mappings.put("skip", NodeBox.class);
        this.mappings.put(MetaBox.fourcc(), MetaBox.class);
        this.mappings.put(DataRefBox.fourcc(), DataRefBox.class);
        this.mappings.put("ipro", NodeBox.class);
        this.mappings.put("sinf", NodeBox.class);
        this.mappings.put(ChunkOffsets64Box.fourcc(), ChunkOffsets64Box.class);
        this.mappings.put(SoundMediaHeaderBox.fourcc(), SoundMediaHeaderBox.class);
        this.mappings.put("clip", NodeBox.class);
        this.mappings.put(ClipRegionBox.fourcc(), ClipRegionBox.class);
        this.mappings.put(LoadSettingsBox.fourcc(), LoadSettingsBox.class);
        this.mappings.put("tapt", NodeBox.class);
        this.mappings.put("gmhd", NodeBox.class);
        this.mappings.put("tmcd", Box.LeafBox.class);
        this.mappings.put("tref", NodeBox.class);
        this.mappings.put("clef", ClearApertureBox.class);
        this.mappings.put("prof", ProductionApertureBox.class);
        this.mappings.put("enof", EncodedPixelBox.class);
        this.mappings.put(GenericMediaInfoBox.fourcc(), GenericMediaInfoBox.class);
        this.mappings.put(TimecodeMediaInfoBox.fourcc(), TimecodeMediaInfoBox.class);
        this.mappings.put(UdtaBox.fourcc(), UdtaBox.class);
        this.mappings.put(CompositionOffsetsBox.fourcc(), CompositionOffsetsBox.class);
        this.mappings.put(NameBox.fourcc(), NameBox.class);
        this.mappings.put("mdta", Box.LeafBox.class);
        this.mappings.put(MovieFragmentHeaderBox.fourcc(), MovieFragmentHeaderBox.class);
        this.mappings.put(TrackFragmentHeaderBox.fourcc(), TrackFragmentHeaderBox.class);
        this.mappings.put(MovieFragmentBox.fourcc(), MovieFragmentBox.class);
        this.mappings.put(TrackFragmentBox.fourcc(), TrackFragmentBox.class);
        this.mappings.put(TrackFragmentBaseMediaDecodeTimeBox.fourcc(), TrackFragmentBaseMediaDecodeTimeBox.class);
        this.mappings.put(TrunBox.fourcc(), TrunBox.class);
    }
}