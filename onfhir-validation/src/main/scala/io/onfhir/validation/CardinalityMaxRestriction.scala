package io.onfhir.validation

import io.onfhir.api.validation.{ConstraintFailure, FhirRestriction}
import org.json4s.JsonAST.{JArray, JValue}

/**
 * Max cardinality restriction (If a max cardinality other than * is given)
 *
 * @param n
 */
case class CardinalityMaxRestriction(n:Int) extends  FhirRestriction {
  override def evaluate(value:JValue):Seq[ConstraintFailure] = {
    val (isOk, actual) =
      value match {
        case JArray(arr) => (arr.length <= n) -> arr.length //If array it should have less or equal elements
        case _ => true -> 0
      }
    if(!isOk)
      Seq(ConstraintFailure(s"Max cardinality expected '$n' does not match with actual cardinality $actual!"))
    else
      Nil
  }
}