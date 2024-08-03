package info.journeymap.shaded.kotlin.kotlin.random;

import info.journeymap.shaded.kotlin.kotlin.Metadata;
import info.journeymap.shaded.org.jetbrains.annotations.NotNull;
import java.io.Serializable;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\b\u0000\u0018\u0000 \u00122\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001\u0012B\u0017\b\u0010\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007B7\b\u0000\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0005¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0005H\u0016R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013" }, d2 = { "Linfo/journeymap/shaded/kotlin/kotlin/random/XorWowRandom;", "Linfo/journeymap/shaded/kotlin/kotlin/random/Random;", "Ljava/io/Serializable;", "Linfo/journeymap/shaded/kotlin/kotlin/io/Serializable;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "Companion", "info.journeymap.shaded.kotlin.kotlin-stdlib" })
public final class XorWowRandom extends Random implements Serializable {

    @NotNull
    private static final XorWowRandom.Companion Companion = new XorWowRandom.Companion(null);

    private int x;

    private int y;

    private int z;

    private int w;

    private int v;

    private int addend;

    @Deprecated
    private static final long serialVersionUID = 0L;

    public XorWowRandom(int x, int y, int z, int w, int v, int addend) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.v = v;
        this.addend = addend;
        byte var7 = (this.x | this.y | this.z | this.w | this.v) != 0;
        if (!var7) {
            ???;
            String var14 = "Initial state must have at least one non-zero element.";
            throw new IllegalArgumentException(var14.toString());
        } else {
            var7 = 64;
            int var8 = 0;
            while (var8 < var7) {
                var8++;
                ???;
                this.nextInt();
            }
        }
    }

    public XorWowRandom(int seed1, int seed2) {
        this(seed1, seed2, 0, 0, ~seed1, seed1 << 10 ^ seed2 >>> 4);
    }

    @Override
    public int nextInt() {
        int t = this.x;
        t ^= t >>> 2;
        this.x = this.y;
        this.y = this.z;
        this.z = this.w;
        int v0 = this.v;
        this.w = v0;
        t = t ^ t << 1 ^ v0 ^ v0 << 4;
        this.v = t;
        this.addend += 362437;
        return t + this.addend;
    }

    @Override
    public int nextBits(int bitCount) {
        return RandomKt.takeUpperBits(this.nextInt(), bitCount);
    }

    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005" }, d2 = { "Linfo/journeymap/shaded/kotlin/kotlin/random/XorWowRandom$Companion;", "", "()V", "serialVersionUID", "", "info.journeymap.shaded.kotlin.kotlin-stdlib" })
    private static final class Companion {

        private Companion() {
        }
    }
}