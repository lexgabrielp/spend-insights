import {type ClassValue, clsx} from 'clsx';
import {twMerge} from 'tailwind-merge';

export function cn(...i: ClassValue[]) {
    return twMerge(clsx(i));
}

export const money = (n: number) => new Intl.NumberFormat('en-PH', {style: 'currency', currency: 'PHP'}).format(n || 0);
