package info.journeymap.shaded.kotlin.kotlin.random;

import info.journeymap.shaded.kotlin.kotlin.Metadata;
import info.journeymap.shaded.kotlin.kotlin.jvm.internal.Intrinsics;
import info.journeymap.shaded.org.jetbrains.annotations.NotNull;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\bH\u0016J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017" }, d2 = { "Linfo/journeymap/shaded/kotlin/kotlin/random/AbstractPlatformRandom;", "Linfo/journeymap/shaded/kotlin/kotlin/random/Random;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "nextDouble", "", "nextFloat", "", "nextInt", "until", "nextLong", "", "info.journeymap.shaded.kotlin.kotlin-stdlib" })
public abstract class AbstractPlatformRandom extends Random {

    @NotNull
    public abstract java.util.Random getImpl();

    @Override
    public int nextBits(int bitCount) {
        return RandomKt.takeUpperBits(this.getImpl().nextInt(), bitCount);
    }

    @Override
    public int nextInt() {
        return this.getImpl().nextInt();
    }

    @Override
    public int nextInt(int until) {
        return this.getImpl().nextInt(until);
    }

    @Override
    public long nextLong() {
        return this.getImpl().nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return this.getImpl().nextBoolean();
    }

    @Override
    public double nextDouble() {
        return this.getImpl().nextDouble();
    }

    @Override
    public float nextFloat() {
        return this.getImpl().nextFloat();
    }

    @NotNull
    @Override
    public byte[] nextBytes(@NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        ???;
        this.getImpl().nextBytes(array);
        return array;
    }
}