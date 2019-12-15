
def add(curser_pos, opcode):
    sum = opcode[opcode[curser_pos+1]] + opcode[opcode[curser_pos+2]]
    opcode[opcode[curser_pos + 3]] = sum
    return opcode

def mult(curser_pos, opcode):
    sum = opcode[opcode[curser_pos+1]] * opcode[opcode[curser_pos+2]]
    opcode[opcode[curser_pos + 3]] = sum
    return opcode

if __name__ == '__main__':
    filename = 'input'
    line = ''
    
    switch = {
        1: add,
        2: mult
    }
    
    with open(filename, 'r') as f:
        line = f.readline()
        
    opcode_list_str = line.split(',')
    opcode_list = [int(num) for num in opcode_list_str]
    
    # Change input values before running https://adventofcode.com/2019/day/2
    opcode_list[1] = 12
    opcode_list[2] = 2 
    
    curser_pos = 0
    
        
    while(opcode_list[curser_pos] != 99):
        opcode_list = switch[opcode_list[curser_pos]](curser_pos, opcode_list)
        curser_pos += 4
    
    print(opcode_list[0]) # return value at postiion 0