import * as React from 'react';
import {cn} from '@/lib/utils';

export function Button({className, ...props}: React.ButtonHTMLAttributes<HTMLButtonElement>) {
    return <button
        className={cn('rounded-xl bg-slate-950 px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:scale-[1.01] disabled:opacity-50 dark:bg-white dark:text-slate-950', className)} {...props}/>;
}
