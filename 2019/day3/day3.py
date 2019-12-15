import math

def distance(s_x, s_y, d_x, d_y) -> float:
    return abs(s_x-d_x) + abs(s_y-d_y)

def gen_path_coordinates(path):
    """
    generates the set of coordinates arising from the path
    
    path = [R200, D200, etc..]
    
    """
    
    switch = {
        'R': (1, 0),
        'L': (-1, 0),
        'U': (0, 1),
        'D': (0, -1)
    }
    
    coordinates = {(0,0)}
    curr_pos = (0, 0)
    for instruction in path:
        # get direction
        direction = instruction[0]
        nsteps = int(instruction[1:])
        x_dir, y_dir = switch[direction]
        
        coor_temp = { (curr_pos[0] + (x_dir * step), curr_pos[1] + (y_dir * step)) for step in range(1, nsteps+1) }
        
        coordinates.update(coor_temp) # update set coordinates
        
        curr_pos = (curr_pos[0] + (x_dir*nsteps), curr_pos[1] + (y_dir*nsteps))
        
    return coordinates


if __name__ == '__main__':
    line1 = ''
    line2 = ''
    file = 'input'
    
    with open(file, 'r') as f:
        line1 = f.readline()
        line2 = f.readline()
    
    path1 = line1.split(',')
    path2 = line2.split(',') 
    
    cset1 = gen_path_coordinates(path1)
    cset2 = gen_path_coordinates(path2)
    
    c_intersect = cset1 & cset2
    if (0, 0) in c_intersect:
        c_intersect.remove((0, 0))  
    
    min_distance = math.inf
    for x,y in c_intersect:
        manhattan_dist = distance(0, 0, x, y)
        if min_distance > manhattan_dist:
            min_distance = manhattan_dist
    
    print(f"Min_distance: {min_distance}")
