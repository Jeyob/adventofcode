
def add(curser_pos, opcode):
    sum = opcode[opcode[curser_pos+1]] + opcode[opcode[curser_pos+2]]
    opcode[opcode[curser_pos + 3]] = sum
    return opcode

def mult(curser_pos, opcode):
    sum = opcode[opcode[curser_pos+1]] * opcode[opcode[curser_pos+2]]
    opcode[opcode[curser_pos + 3]] = sum
    return opcode

def find_pair(opcode_list, switch):
        
    curser_pos = 0
                
    while(opcode_list[curser_pos] != 99):
        opcode_list = switch[opcode_list[curser_pos]](curser_pos, opcode_list)
        curser_pos += 4


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
    opcode_list_org = [int(num) for num in opcode_list_str]
    
    i_max, j_max = [100, 100]
    for i in range(0, i_max):
        for j in range(0, j_max):
            opcode_list = opcode_list_org.copy()
            opcode_list[1] = i # noun
            opcode_list[2] = j # verb
            
            find_pair(opcode_list, switch)
            
            if opcode_list[0] == 19690720:
                print("Noun: {} \nVerb: {}".format(opcode_list[1], opcode_list[2]))
                print("100 * noun + verb = {}".format(100 * opcode_list[1] + opcode_list[2]))