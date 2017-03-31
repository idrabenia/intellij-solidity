package me.serce.solidity.lang.core.resolve

class SolVarResolveTest : SolResolveTestBase() {
  fun testLocal1() = checkByCode("""
        contract B {
            function B() {
                var a0 = 0;
                var a = 0;
                  //x
                var b = 1;
                a = a++;
              //^
            }
        }
  """)

  fun testLocal2() = checkByCode("""
        contract B {
            function B() {
                var a0 = 0;
                var a = 0;
                  //x
                var b = 1;
                a = a++;
                  //^
            }
        }
  """)

  fun testField1() = checkByCode("""
        contract B {
            uint public lastC;
                        //x

            function B() {
                lastC = 1;
                //^
            }
        }
  """)

  fun testFunctionParams() = checkByCode("""
        contract B {
            function B(uint abc) {
                          //x
                _;
                abc = 1;
                //^
            }
        }
  """)

  fun testFunctionParamsSecond() = checkByCode("""
        contract B {
            function B(uint abc1, uint abc2) {
                                       //x
                abc1 = 1;
                abc2 = 1;
                //^
            }
        }
  """)

  fun testResolveStateInheritance() = checkByCode("""

        contract C {
            uint abc;
                //x
        }

        contract B {}

        contract A is B, C {
            function A() {
                abc = 1;
                //^
            }
        }
  """)

}