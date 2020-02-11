
def is_valid_password(password:int)->bool:
    prev = 0
    adjacent = False
    adjacent_seen = [0] * 10
    adjacent_not_larger = False

    for c in str(password):
        c_int = int(c)
        if c_int > prev:
            prev = c_int
        elif c_int == prev:
            prev = c_int
            adjacent_seen[c_int] += 1
        else:
            return False

    adjacent = sum(adjacent_seen) > 0
    for i in adjacent_seen:
        if i == 1:
            adjacent_not_larger = True

    return True if adjacent and adjacent_not_larger else False


def number_of_passwords(input_data: range) -> int:
    npasswords = 0
    for value in input_data:
        if is_valid_password(value):
            npasswords+=1

    return npasswords


if __name__ == '__main__':
    input_data = range(134564, 585159)
    print(number_of_passwords(input_data))