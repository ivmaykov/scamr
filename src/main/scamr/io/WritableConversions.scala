package scamr.io

import org.apache.hadoop.io._

object WritableConversions {
  // Null type
  implicit def NullWritableToNull(value: NullWritable): Null = null
  implicit def NullToNullWritable(value: Null): NullWritable = NullWritable.get

  // Booleans
  implicit def BooleanWritableToBoolean(value: BooleanWritable): Boolean = value.get
  implicit def BooleanToBooleanWritable(value: Boolean): BooleanWritable = new BooleanWritable(value)

  // Fixed-length integer types
  implicit def ByteWritableToByte(value: ByteWritable): Byte = value.get
  implicit def ByteToByteWritable(value: Byte): ByteWritable = new ByteWritable(value)

  implicit def IntWritableToInt(value: IntWritable): Int = value.get
  implicit def IntToIntWritable(value: Int): IntWritable = new IntWritable(value)

  implicit def LongWritableToLong(value: LongWritable): Long = value.get
  implicit def LongToLongWritable(value: Long): LongWritable = new LongWritable(value)

  // Variable-length integer types
  implicit def VIntWritableToInt(value: VIntWritable): Int = value.get
  implicit def IntToVIntWritable(value: Int): VIntWritable = new VIntWritable(value)

  implicit def VLongWritableToLong(value: VLongWritable): Long = value.get
  implicit def LongToVLongWritable(value: Long): VLongWritable = new VLongWritable(value)

  // Floating-point types
  implicit def FloatWritableToFloat(value: FloatWritable): Float = value.get
  implicit def FloatToFloatWritable(value: Float): FloatWritable = new FloatWritable(value)

  implicit def DoubleWritableToDouble(value: DoubleWritable): Double = value.get
  implicit def DoubleToDoubleWritable(value: Double): DoubleWritable = new DoubleWritable(value)

  // Strings and string-like things
  implicit def TextToString(value: Text): String = value.toString
  implicit def StringToText(value: String): Text = new Text(value)

  implicit def StringBuilderToText(value: StringBuilder): Text = new Text(value.toString)
  implicit def StringBufferToText(value: StringBuffer): Text = new Text(value.toString)

  // Maps and sorted maps
  implicit def MapToMapWritable[K <: Writable, V <: Writable](value: Map[K,V]): MapWritable = {
    val mapWritable = new MapWritable
    mapWritable.putAll(value)
    mapWritable
  }

  implicit def MapToSortedMapWritable[K <: WritableComparable[K], V <: Writable](value: Map[K,V]): SortedMapWritable = {
    val sortedMapWritable = new SortedMapWritable
    sortedMapWritable.putAll(value)
    sortedMapWritable
  }

  // Typed ArrayWritables
  implicit def BooleanArrayToBooleanArrayWritable(value: Array[Boolean]): BooleanArrayWritable =
    new BooleanArrayWritable(value.map(new BooleanWritable(_)))
  implicit def BooleanArrayWritableToBooleanArray(value: BooleanArrayWritable): Array[Boolean] =
    value.get.asInstanceOf[Array[BooleanWritable]].map(_.get)

  implicit def FloatArrayToFloatArrayWritable(value: Array[Float]): FloatArrayWritable =
    new FloatArrayWritable(value.map(new FloatWritable(_)))
  implicit def FloatArrayWritableToFloatArray(value: FloatArrayWritable): Array[Float] =
    value.get.asInstanceOf[Array[FloatWritable]].map(_.get)

  implicit def DoubleArrayToDoubleArrayWritable(value: Array[Double]): DoubleArrayWritable =
    new DoubleArrayWritable(value.map(new DoubleWritable(_)))
  implicit def DoubleArrayWritableToDoubleArray(value: DoubleArrayWritable): Array[Double] =
    value.get.asInstanceOf[Array[DoubleWritable]].map(_.get)

  implicit def ByteArrayToBytesWritable(value: Array[Byte]): BytesWritable = new BytesWritable(value)
  // Make a copy of the contents, because a BytesWritable is mutable. All other writables wrap immutable
  // objects, so this is highly desirable for consistency.
  implicit def BytesWritableToByteArray(value: BytesWritable): Array[Byte] =
    if (value.getLength == 0) {
      new Array[Byte](0)
    } else {
      val bytes = new Array[Byte](value.getLength)
      Array.copy(value.getBytes, 0, bytes, 0, value.getLength)
      bytes
    }

  implicit def IntArrayToIntArrayWritable(value: Array[Int]): IntArrayWritable =
    new IntArrayWritable(value.map(new IntWritable(_)))
  implicit def IntArrayWritableToIntArray(value: IntArrayWritable): Array[Int] =
    value.get.asInstanceOf[Array[IntWritable]].map(_.get)

  implicit def LongArrayToLongArrayWritable(value: Array[Long]): LongArrayWritable =
    new LongArrayWritable(value.map(new LongWritable(_)))
  implicit def LongArrayWritableToLongArray(value: LongArrayWritable): Array[Long] =
    value.get.asInstanceOf[Array[LongWritable]].map(_.get)

  implicit def IntArrayToVIntArrayWritable(value: Array[Int]): VIntArrayWritable =
    new VIntArrayWritable(value.map(new VIntWritable(_)))
  implicit def VIntArrayWritableToIntArray(value: VIntArrayWritable): Array[Int] =
    value.get.asInstanceOf[Array[VIntWritable]].map(_.get)

  implicit def LongArrayToVLongArrayWritable(value: Array[Long]): VLongArrayWritable =
    new VLongArrayWritable(value.map(new VLongWritable(_)))
  implicit def VLongArrayWritableToLongArray(value: VLongArrayWritable): Array[Long] =
    value.get.asInstanceOf[Array[VLongWritable]].map(_.get)

  implicit def StringArrayToTextArrayWritable(value: Array[String]): TextArrayWritable =
    new TextArrayWritable(value.map(new Text(_)))
  implicit def TextArrayWritableToStringArray(value: TextArrayWritable): Array[String] =
    value.get.asInstanceOf[Array[Text]].map(_.toString)
}