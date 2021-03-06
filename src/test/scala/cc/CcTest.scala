package cc

import org.junit.Test
import cc.CcParser._

import CcProperties._

class CcTest {
  @Test
  def simpleTests {
    val exp = cc"λ X: ●. λx:X. x "

    assert(exp == exp.norm)

    assert(exp.ty() == Some(cc"Π X: ●. Πx:X. X "))

    assert(cc"(λ X: ●. λx:X. x) " == exp)

    assert(cc"""( 
      (λ X: ●. λx:X. x)
      (Π X: ●. Πx:X. X)
      (λ X: ●. λx:X. x)
      )
      """.norm == exp)
  }

  @Test
  def smallstepMatchesBigStepTests {
    val e = cc"λ A : ● . λ B : □ . (● ([λ C : □ . □] □))"

    assert(e.norm == e.smallStep)
  }
}