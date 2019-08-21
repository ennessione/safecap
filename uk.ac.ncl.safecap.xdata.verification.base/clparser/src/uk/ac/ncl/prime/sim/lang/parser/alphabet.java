package uk.ac.ncl.prime.sim.lang.parser;

public class alphabet {
  public static final int LPAR 		= 1;
  public static final int RPAR 		= 2;
  public static final int LSQB 		= 3;
  public static final int RSQB 		= 4;

  public static final int BOOL 		= 5;
  public static final int INT 		= 6;
  public static final int NAT 		= 7;
  public static final int NAT1 		= 8;
  public static final int SEQ 		= 9;
  public static final int SET 		= 10;


  public static final int IF 		= 13;
  public static final int ELSE 		= 15;
  public static final int BLOCK		= 16;
  public static final int STM_LIST	= 17;

  public static final int OP_PLUS 	= 26;
  public static final int OP_MINUS 	= 27;
  public static final int OP_MUL 	= 28;
  public static final int OP_DIV 	= 29;

  public static final int OP_LSS 	= 30;
  public static final int OP_GRT 	= 31;
  public static final int OP_LEQ 	= 32;
  public static final int OP_GEQ 	= 33;
  public static final int OP_MOD 	= 34;

  public static final int OP_SETMINUS 	= 35;
  public static final int OP_UNION 	= 36;
  public static final int OP_INTER 	= 37;
  public static final int OP_RAN 	= 38;
  public static final int OP_DOM 	= 39;
  public static final int OP_CARD 	= 40;
  public static final int OP_DOMRES 	= 41;
  public static final int OP_DOMSUB 	= 42;	
  public static final int OP_EMPTYSET 	= 43;

  public static final int OP_EQL 	= 44;
  public static final int OP_NEQ 	= 45;
  public static final int OP_AND 	= 46;
  public static final int OP_OR 	= 47;

  public static final int OP_COMMA 	= 48;
  public static final int OP_SEMIC 	= 49;
  public static final int OP_POINT 	= 50;
  public static final int OP_IN 	= 51;
  public static final int REAL 		= 52;
  public static final int OP_MAP 	= 53;
  public static final int EXP_LIST 	= 54;
  public static final int PAR_ASSIGN= 55;


  public static final int ID 		= 102;
  public static final int NUMBER 	= 103;
  public static final int OP_UNARY_MINUS= 104;

  public static final int OP_NOT	= 205;

  public static final int LCUR 		= 206;
  public static final int RCUR 		= 207;
  public static final int CART		= 210;
  public static final int FAPP		= 212;
  public static final int IMAGE		= 213;
  public static final int SEQC		= 214;
  public static final int OP_CONVERSE	= 215;
  public static final int OP_RANRES 	= 216;
  public static final int OP_RANSUB 	= 217;
  public static final int OP_OVR 	= 218;
  public static final int OP_FCOMP 	= 219;
  public static final int OP_GEN_UNION 	= 220;
  public static final int OP_GEN_INTER 	= 221;
  public static final int OP_MIN 	= 222;
  public static final int OP_MAX 	= 223;
  public static final int OP_SUM 	= 224;
  public static final int OP_EMPTYSEQ 	= 225;
  public static final int OP_RANGE 	= 226;
  public static final int OP_PRJ1 	= 227;
  public static final int OP_PRJ2 	= 228;
  public static final int OP_SOME 	= 229;
  public static final int OP_TRUE 	= 231;
  public static final int OP_FALSE 	= 232;
  public static final int OP_SUBSET 	= 233;
  public static final int OP_SUBSETEQ 	= 234;
  public static final int OP_DOT 	= 235;
  public static final int ASSERTION	= 238;
  public static final int OP_PRIME	= 254;
  public static final int STRING	= 257;
  public static final int XTAG		= 258;
  public static final int OP_IMPLIES 	= 259;
  public static final int BCMSUCH	= 304;
  public static final int OP_NOTIN	= 306;
  public static final int OP_FINITE	= 307;
  public static final int OP_QUAL	= 309;
  public static final int TYPE_ANY	= 312;
  public static final int TYPE_UNIT	= 313;
  public static final int TYPE_REAL	= 314;
  public static final int OP_DIRECT_PRODUCT= 315;



  public static final int B_FORALL	= 401;
  public static final int B_EXISTS	= 402;
  public static final int B_SETCOMP	= 403;
  public static final int B_LAMBDA	= 404;


  public static final int OP_PFUN	= 501;
  public static final int OP_REL	= 502;
  public static final int OP_PINJ	= 504;
  public static final int OP_SET	= 505;
  public static final int OP_SEQ	= 506;
  public static final int OP_CART	= 507;
  public static final int OP_INT	= 508;
  public static final int OP_BOOL	= 509;

  public static final int SKIP		= 1009;
  public static final int SETC		= 1010;
  public static final int BCMEQ		= 1014;
  public static final int BCMEQ_SEQ	= 1015;
  public static final int TYPED_ID_LIST	= 1017;
  public static final int TYPED_ID	= 1018;
  
  public static final int ID_TEMPL_TYPE	= 1020; 
  public static final int TYPE_NULL	= 1021; 
  
}

