package cc

import cc.Cc._

object CcProperties {
  //TODO: can do some implicit magic so that it looks like these are members of Exp without cluttering the definition

  def removeBinder(vs: Set[Var]): Set[Var] = vs.filter(_.v != 0).map(va => Var(va.v - 1))

  def freeVars(e: Exp): Set[Var] = e match {
    case Var(i)       => Set(Var(i))

    case Prop()       => Set()
    case Typ()        => Set()

    case Lam(ty, bod) => freeVars(ty) ++ removeBinder(freeVars(bod))
    case Pi(ty, bod)  => freeVars(ty) ++ removeBinder(freeVars(bod))

    case App(f, a)    => freeVars(f) ++ freeVars(a)
  }

}