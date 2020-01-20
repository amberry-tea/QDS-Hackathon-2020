import math
import scipy.stats as stats
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import xlsxwriter

df = pd.read_json (r'C:\Users\Andrew\Desktop\BCIT_Work\QDS_Hacks_2020\data\CYCLES.json')

e = df["LOADING_TIME"]
array = []
arrayValX = []

#workbook = xlsxwriter.Workbook("arrays.xlsx")
#worksheet = workbook.add_worksheet()

for i in range(30*60):
    array.append(0)

for i in range(30*60):
    arrayValX.append(i)

for x in e:
    if x < 30:
        array[round(x*60)] += 1

def plot_bar_x():
    # this is for plotting purpose
    index = np.arange(len(array))
    plt.bar(arrayValX, array, width = 1.0)
    plt.xlabel('Time (seconds)', fontsize=12)
    plt.ylabel('Frequency', fontsize=12)
    #plt.xticks(index, label, fontsize=5, rotation=30)
    plt.title('Seconds spent loading the truck')
    plt.show()

plot_bar_x()

#col = 1;

#for y in array:
#    worksheet.write_column(row, col, data)

#workbook.close()
