package cc

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Prop._

import org.scalacheck._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import cc.Cc.Var
import cc.Cc.Exp
import cc.Cc.Prop

import org.scalacheck.ScalacheckShapeless._

import CcProperties._

object CcParserSpec extends Properties("CcParserSpec") {

  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(1000) // 1000000 is good for a coffee break :)

  def genVar: Gen[Var] = {
    for {
      i <- Gen.choose(0, 25)
    } yield Var(i)
  }

  implicit def shrinkExp: Shrink[Var] = Shrink { case _ => Stream() }

  implicit val arbVar: Arbitrary[Var] = Arbitrary(genVar)

  property("can always parse a closed term") = forAll { e: Exp =>
    e.freeVars.isEmpty ==> {
      CcParser.parse(e.toString()) match {
        case CcParser.Success(e2, _) => e == e2
        case _                       => false
      }
    }
  }

  // the above test makes this redundent
  property("can always parse a closed term") = forAll { e: Exp =>
    e.freeVars.isEmpty ==> {
      CcParser.parse(e.toString()).successful
    }
  }

}