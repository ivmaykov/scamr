package scamr.mapreduce.reducer

import scamr.mapreduce.lambda.BaseLambdaReducer
import org.apache.hadoop.conf.Configuration

class LambdaReducer[K1, V1, K2, V2] extends BaseLambdaReducer[K1, V1, K2, V2] {
  override val functionPropertyName = BaseLambdaReducer.ReduceFunctionProperty
}

object LambdaReducer {
  def setLambdaFunction[K1, V1, K2, V2](conf: Configuration, lambda: BaseLambdaReducer[K1, V1, K2, V2]#FunctionType) {
    BaseLambdaReducer.setLambdaReduceFunction(conf, lambda)
  }
}