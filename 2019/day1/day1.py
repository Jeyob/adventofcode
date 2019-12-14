import math

def fuel_required(mass):
    return math.floor(mass / 3) - 2

if __name__ == '__main__':
    try:
        total_requried = 0
        with open('input') as f:
            for line in f.readlines():
                total_requried += fuel_required(int(line))
        
        print("total requried: {}".format(total_requried))
         
    except FileNotFoundError as e:
        print('File could not be found')
    