package com.example.phoenix.healthcare;

import java.util.Random;

public class Generator {

    static int lvl=0;
    static int last=70;
    static double der=0.7;

    /**
     *
     * @return
     */
    public static int getGM() {
        double gm=0;
        Random rand=new Random();
        if(rand.nextInt(12)!=6)
        {
            double ttt=((Math.random() * 15))/10;
            if(rand.nextInt(2)==0)
                if((gm=der-ttt)<0)
                    gm=0;
                else
                    gm=der+ttt;

            if(rand.nextInt(12)!=8)
            {if(gm>=1.1 || gm<0.5)
                gm=((Math.random() * 6) + 5)/10;}

        }
        else
            gm=(Math.random() * 20)/10;

        der=gm;
        gm=gm*100;

        return (int)gm;
    }

    public static int getBC() {
        int bc=0;
        Random rand=new Random();
        if(rand.nextInt(12)!=6)
        {
            int ttt=rand.nextInt(10);
            if(rand.nextInt(2)==0)
                bc=last-ttt;
            else
                bc=last+ttt;

            if(rand.nextInt(12)!=8)
            {if(bc>=100 || bc<50)
                bc=(int) (Math.random() * 50) + 50;}

        }
        else
            bc=rand.nextInt(400);

        last=bc;
        return bc;
    }

    public static int[] getTM() {
        int tm1 = 0,tm2=0;
        System.out.println("Level = "+lvl);

        Random rand=new Random();
        if(rand.nextInt(12)!=6)
        {
            if(lvl==1)
            {
                tm1=(int) (Math.random() * 4) + 10;
                tm2=8;

                if(rand.nextInt(8)==4)
                    lvl=0;
                else
                    lvl=rand.nextInt(2)+1;
            }
            else
            if(lvl==2)
            {
                tm1=(int) (Math.random() * 2) + 14;
                tm2=(int) (Math.random() * 2) + 9;

                int ttt=rand.nextInt(7);
                if(ttt!=0 && ttt!=1)
                    lvl=1;
                else
                if(ttt==0)
                    lvl=2;
                else
                if(ttt==1)
                    lvl=3;
            }


            else
            if(lvl==0)
            {
                tm1=(int) (Math.random() * 11);
                tm2=(int) (Math.random() * 7);
                lvl=rand.nextInt(2);
            }
            else
            if(lvl==3)
            {
                tm1=(int) (Math.random() * 6) + 18;
                tm2=(int) (Math.random() * 2) + 10;

                if(rand.nextInt(5)!=0)
                    lvl=2;
                else
                    lvl=3;
            }

        }
        else
        {
            System.out.println("BAD LVL");
            tm1=rand.nextInt(24);
            tm2=rand.nextInt(24);}


        int res[]= {tm1,tm2};

        return res;
    }



}
