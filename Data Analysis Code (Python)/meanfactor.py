import math
import scipy.stats as stats
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

#delete zeros!!!!!1

df = pd.read_json (r'C:\Users\Andrew\Desktop\BCIT_Work\QDS_Hacks_2020\data\CYCLES.json')
df2 = pd.read_json (r'C:\Users\Andrew\Desktop\BCIT_Work\QDS_Hacks_2020\data\PRODUCTIVITY.json')

date = 0
daytime = 1
dateIndex = 0
farray = []
parray = []

while dateIndex < 29:
    date = df["SHIFT_DATE"].unique()[dateIndex]
    dateIndex += 1

    t = df.query("SHIFT_IDENT == " + str(daytime), inplace = False)
    t = t.query("SHIFT_DATE == " + str(date), inplace = False)
    tnum = len(t["TRUCK_ID"].unique())
    #print(tnum);

    s = df.query("SHIFT_IDENT == " + str(daytime), inplace = False)
    s = s.query("SHIFT_DATE == " + str(date), inplace = False)
    snum = len(s["SHOVEL_ID"].unique())
    #print(snum)

    s = df.query("SHIFT_IDENT == " + str(daytime), inplace = False)
    s = s.query("SHIFT_DATE == " + str(date), inplace = False)
    sloadtotal = 0;
    for x in s["LOADING_TIME"]:
        sloadtotal+= x;
    for x in s["SPOTTING_TIME"]:
        sloadtotal+= x;
    savg = sloadtotal / len(s["LOADING_TIME"])
    #print(savg)

    t = df.query("SHIFT_IDENT == " + str(daytime), inplace = False)
    t = t.query("SHIFT_DATE == " + str(date), inplace = False)
    tloadtotal = 0;
    for x in t["EMPTY_TIME"]:
        tloadtotal+= x;
    for x in t["QUEUE_AT_LU"]:
        tloadtotal+= x;
    for x in t["SPOTTING_TIME"]:
        tloadtotal+= x;
    for x in t["LOADING_TIME"]:
        tloadtotal+= x;
    for x in t["HAULING_TIME"]:
        tloadtotal+= x;
    for x in t["WAIT_AT_DUMP"]:
        tloadtotal+= x;
    for x in t["DUMPING_TIME"]:
        tloadtotal+= x;
    tavg = tloadtotal / len(t["LOADING_TIME"])
    #print(tavg)

    #METRIC FACTOR
    farray.append((tnum / snum) * (savg / tavg))
    
    h = df2.query("SHIFT_DATE == " + str(date), inplace = False);
    h = h.query("SHIFT_IDENT == " + str(daytime), inplace = False)
    for x in h["PRODUCTIVITY"]:
        parray.append(x);

print("Mean Factor")
for x in farray:
    print(x)
print()
print("Productivity")
for x in parray:
    print(x)


    


