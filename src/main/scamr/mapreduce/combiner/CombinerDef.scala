package scamr.mapreduce.combiner

import org.apache.hadoop.mapreduce.Reducer
import scamr.conf.{LambdaConfModifier, ConfModifier}
import scamr.mapreduce.reducer.SimpleReducer

object CombinerDef {
  implicit def classicCombinerToDef[K2, V2](classicCombiner: Reducer[K2, V2, K2, V2]) =
    new ClassicCombinerDef(classicCombiner)
  implicit def simpleReducerClassToDef[K2, V2](clazz: Class[_ <: SimpleReducer[K2, V2, K2, V2]]) =
    new SimpleCombinerDef(clazz)
  implicit def lambdaCombineFunctionToDef[K2, V2](lambda: LambdaCombiner[K2, V2]#FunctionType) =
    new LambdaCombinerDef(lambda)
}

trait CombinerDef[K2, V2] {
  val combinerClass: Option[Class[_ <: Reducer[K2, V2, K2, V2]]]
  val confModifiers: List[ConfModifier]
}

class ClassicCombinerDef[K2, V2](val classicCombiner: Reducer[K2, V2, K2, V2]) extends CombinerDef[K2, V2] {
  override val combinerClass = Some(classicCombiner.getClass)
  override val confModifiers = List()
}

class SimpleCombinerDef[K2, V2](val simpleCombinerClass: Class[_ <: SimpleReducer[K2, V2, K2, V2]])
    extends CombinerDef[K2, V2] {
  override val combinerClass = Some(classOf[SimpleCombiner.Runner[K2, V2]])
  override val confModifiers =
    List(LambdaConfModifier { conf => SimpleCombiner.setSimpleCombinerClass(conf, simpleCombinerClass) })
}

class LambdaCombinerDef[K2, V2](val lambdaCombineFunction: LambdaCombiner[K2, V2]#FunctionType)
    extends CombinerDef[K2, V2] {
  override val combinerClass = Some(classOf[LambdaCombiner[K2, V2]])
  override val confModifiers =
    List(LambdaConfModifier { conf => LambdaCombiner.setLambdaFunction(conf, lambdaCombineFunction) })
}

